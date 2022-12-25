;**
;**	Logics for room 213 -- Park Bench
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 23, 1989
;**

(script#				213)

(public
	rm213				0
)


;**		newspaperState gets changed in these rooms at these times:
;**	NSnotYet								;**	000 at start of game
;**	NSshowroom							;**	260 when L gets knife from T
;**	NSpComing							;**	435 when L learns C wants a farm
;**	NSpHere								;**	440 when L removes Ziegfield suit
;**	NSlMissing							;**	483 when L becomes P



(define	vRoom 						213)
	(define	lPaper				0)
	(define	lTV					1)
	(define	lRiver				2)
	(define	lRiver2				3)

(define	vLroom				(+ 1 213))
	(define	lSitting				0)
	(define	lReadingPaper		1)

(define	vMusicBy						215)
	(define	lMusicBy				0)
	(define	lComposer			1)


(define	sWaterGurgling				213)	


(define nearPaper		(ego inRect 170 161 221 187))
(define nearTv			(ego inRect 134 125 164 131))


(enum												;**	RoomScript state values
	intro
	moveUp
	walkOver
	gotoBench
	atBench
	sitting
	reading1
	reading2
	reading3
	gettingUp
	standing
	allClear
)


(enum												;**	CreditsScript state values
	doCredits
	doCredits1
	doCredits2
	doCredits3
	clearCredits
)


(local
	seenMsg
	havePaper
)


(instance rm213 of Room
	(properties
		picture		213
		horizon		5
		east			210
	)

	(method (init)
		(super init)

		(if (TstFlg forceAtest)
			(= newspaperState NSpComing)
		)
		(if newspaperState
			(aNewspaper		init)
		)

		(aRiver			init)
		(aRiver2			init)
		(aTv 				init)

		(self
			setScript 		RoomScript
		)

		(if (not (TstFlg sawCredits213))
			(aCredit1			init)
			(aCredit2			init)
		)

		(ego posn 314 163)

		(NormalEgo)
		(ego					init)
		(soundFX
			number			sWaterGurgling,
			loop				FOREVER,
			play,
		)
	)

	(method (newRoom n)
		(music fade)
		(super newRoom n)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)
	)


	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'turn<on/tv, set')
				(cond
					((not nearTv)
						(NotClose)
					)
					(( < (aTv y) MAXBOTTOM)
						(ItIs)
					)
					(else
						(Ok)
						(aTv posn 148 116)
					)
				)
			)

			((Said 'turn<off/tv, set')
				(cond
					((not nearTv)
						(NotClose)
					)
					(( > (aTv y) MAXBOTTOM)
						(ItIs)
					)
					(else
						(Ok)
						(aTv posn 148 1116)
					)
				)
			)

			((Said 'sit')
				(cond
					(egoIsPatti
						(Print "You don't need to do that.")
					)
					((== egoState SITTING)
						(YouAre)
					)
					((!= egoState NORMAL)
						(NotNow)
					)
					(else
						(Ok)
						(self changeState moveUp)
					)
				)
			)

			((or	(Said 'rise, (get<off), (get<up), (stand<up)')
					(Said 'leave/chair'))
				(cond
					((== egoState NORMAL)
						(YouAre)
					)
					((!= egoState SITTING)
						(NotNow)
					)
					(else
						(self changeState gettingUp)
					)
				)
			)

			((Said 'swim')
				(Print "Not in that stream! 
					Have you looked carefully at that water?")
			)

			((Said 'turn, change/channel')
				(if ( < (aTv y) MAXBOTTOM)	;**	It's on.
					(Print "There's only one channel and it's owned by
						Natives, Inc.!")
				else
					(Print "That won't do much good. It's off!")
				)
			)

			((Said 'turn<on/light')
				(Print "They are!")
			)

			((Said 'turn<off/light')
				(Print "You can't do that here; at least, not now.")
			)

			((Said 'feel, look<under, back, behind/table')
				(Print "Congratulations!")
				(Print "You've found...")
				(Print "...nothing!")
			)

			((Said 'look, feel<under/bench')
				(Print "You find some fresh, wet bubble gum, but nothing that
					you'd care to keep!")
			)

			((or	(Said '//tv, set>')
					(Said '/tv, set>'))
				(cond
					((Said 'watch')
						(if ( < (aTv y) MAXBOTTOM)	;**	It's on.
							(Print "Too bad this island doesn't have cable.")
						else
							(Print "You stare at the screen, blankly.")
						)
					)

					((Said 'look')
						(if ( < (aTv y) MAXBOTTOM)	;**	It's on.
							(Print "Too bad this island doesn't have cable.")
						else
							(Print "It looks like it MIGHT work.")
						)
					)

					((Said 'adjust')
						(Print "You can't. Just enjoy it as it is!")
					)

					((Said 'get')
						(Print "You're not strong enough to carry it.")
					)

					((Said 'feel, look<back, under, behind')
						(if ( < (aTv y) MAXBOTTOM)	;**	It's on.
							(Print "OOOOOOWWWWWWWWW!!!!!!!!!")
							(Print "(Don't you know better than to mess around
								the back of a television set when it's on!)" LOW)
							(theGame changeScore -1)
						else
							(Print "There's nothing back there but air.")
						)
					)

					(else
						(Print "Isn't it strange to find a television in a park?")
						(event claimed TRUE)
					)
				)
			)

			((Said 'get/table')
				(Printf "You're after things with TWO legs, %s." egoName)
			)

			((Said 'get/light')
				(Print "Leave the lights alone.")
			)

			((Said 'look/bench')
				(if (== egoState SITTING)
					(Print "Its slats make indentations on your butt.")
				else
					(Print "It's in need of a paint job.")
				)
			)

			((Said 'look>')
				(cond
					((Said '/light')
						(Printf "They just hang around -- rather like you, %s!" egoName)
					)

					((Said '/table')
						(cond
							(havePaper
								(Print "Its polyvinyl chloride surface glistens beneath
									these lights!")
							)
							((cast contains: aNewspaper)
								(Print "There is a newspaper lying on the table.")
							)
							(else
								(Print "It's just a simple end table.")
							)
						)
					)

					((Said '/river, waterfall, water')
						(Print "How nice. Natives, Inc. has created a lovely
							waterfall using the untreated effluent of their sewage.")
					)

					((Said '/ground')
						(Print "These flagstones were made by short, fat, white guys
							in plaid shirts.")
					)

					((Said '[/room]')
						(Print "What an interesting park!
							A lovely television set sits beneath attractive hanging
							lamps facing a park bench_-- all situated near a gurgling
							stream.")
					)
				)
			)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(intro
			)


			;**		Make ego walk over to the park bench and sit down.
			;**	Compensate for below the bench and above the tv.

			(moveUp
				(delay 0)
				(= cycles 0)
				(HandsOff)
				(cond
					(( > (ego y) 171)		;**	Watch out for that TV!
						(ego setMotion MoveTo (ego x) 171 self)
					)
					(( < (ego y) 126)		;**	Avoid the park bench
						(ego setMotion MoveTo (ego x) 126 self)
					)
					(else
						(self cue)
					)
				)
			)

			(walkOver							;**	He's to the side of the bench;
				(cond								;**	move to either end.
					(( > (ego x) 171)
						(ego setMotion MoveTo 171 (ego y) self)
					)
					(( < (ego x) 122)
						(ego setMotion MoveTo 122 (ego y) self)
					)
					(else
						(self cue)
					)
				)
			)

			(gotoBench							;**	He's clear. Go for it.
				(ego setMotion MoveTo (ego x) 171 self)
			)

			(atBench
				(delay 0)
				(= cycles 0)
				(ego
					view 			vLroom,
					setLoop		lSitting,
					setCel		0,
					setCycle		EndLoop self,
				)
			)

			(sitting
				(User canInput TRUE)
				(= egoState SITTING)
				(if (== havePaper TRUE)
					(delay 3)
				)
			)

			(reading1
				(ego
					setCel		0,
					loop 			lReadingPaper,
				)
				(delay (Random 1 4))
			)

			(reading2
				(ego
					setCycle		EndLoop self,
				)
			)

			(reading3
				(ego
					setCycle		BegLoop self,
				)
				(= state (- reading1 1))	;**	When done, start reading1 again.
			)


			;**		This section handles Larry getting up from the park bench.
			;**	If he has the paper, put it back on the table.

			(gettingUp
				(HandsOff)
				(delay 0)						;**	Stop any loose timers.
				(= cycles 0)
				(if havePaper
					(Print "You return the newspaper to the table.")
					(= havePaper FALSE)
					(aNewspaper
						posn		198 190
					)
				)
				(ego
					view 			vLroom,
					setLoop		lSitting,
					setCel		LastCel,
					setCycle		BegLoop self,
				)
			)

			(standing
				(NormalEgo facingNorth)
				(= egoState NORMAL)
			)

			(allClear
				(delay 0)
			)
		)
	)
)


(instance aTv of Prop
	(properties
		view				vRoom
		loop				lTV
		x	 				148
		y					1116
		priority			4
		cycleSpeed		2
	)
	(method (init)
		(super init)
		(self
			setCycle	Forward,
		)
	)
)
(instance aRiver of Prop
	(properties
		view				vRoom
		loop				lRiver
		x	 				31
		y					104
		cycleSpeed		2
	)
	(method (init)
		(super init)
		(self
			isExtra			TRUE,
			setCycle			Forward,
		)
	)
)
(instance aRiver2 of Prop
	(properties
		view				vRoom
		loop				lRiver2
		cel				1
		x	 				0
		y					141
		cycleSpeed		2
	)
	(method (init)
		(super init)
		(self
			isExtra			TRUE,
			setCycle			Forward,
		)
	)
)
(instance aNewspaper of View
	(properties
		view				vRoom
		loop				lPaper
		x					198
		y					190
	)
	(method (init)
		(super init)
		(self
			ignoreActors,
			setPri		15,
			z				(- (aNewspaper y) 169)
			;**		Proper use of z:  set its y to where the priority looks
			;**	right, then subtract where it should be visually.
		)
	)


	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent)
					(event claimed)
					(not (Said '/paper>')))
			(return)
		)

		(if (Said 'get')
			(cond
				(egoIsPatti
					(Print "You don't have time to read, Patti!")
				)
				(havePaper
					(AlreadyTook)
				)
				((not nearPaper)
					(NotClose)
				)
				((and	(!= egoState NORMAL)
						(!= egoState SITTING))
					(NotNow)
				)
				(else
					(Ok)
					(aNewspaper	posn	198 1190)
					(= havePaper TRUE)
					(if (== egoState SITTING)
						(RoomScript changeState sitting)
					else
						(RoomScript changeState moveUp)
					)
				)
			)
		)

		(if (Said 'drop, put, close')
			(if (not havePaper)
				(DontHave)
			else
				(Ok)
				(= havePaper FALSE)
				(aNewspaper
					posn			198 190
				)
				(ego
					view 			vLroom,
					setLoop		lSitting,
					setCel		LastCel,
				)
				(RoomScript
					cycles 		0,
					seconds		0,
					changeState sitting,
				)
			)
		)


		(if (Said 'look')
			(cond
				((not havePaper)
					(DontHave)
				)
				(else
					(switch newspaperState
						(NSpComing
							(Print "Scanning through the island newspaper,
								you find an advertisement for the Casino Lounge...")
							(Print "Coming Soon!\n\n
								Here!\n\n
								At our own Lounge and Piano Bar!\n\n
								Passionate Patti at the Keyboard.\n\n
								Watch for her -- you won't regret it!\n\n"
									#font			bigFont
									#mode			teJustCenter
									#at			-1 30
									#width		234
							)
						)
						(NSshowroom
							(Print "Reading the island newspaper,
								you find an advertisement for the Casino Showroom...")
							(Print "Now Playing!\n
								The big, new, all-girl revue!\n
								It's got T's!\n
								It's got A's!\n
								It's got prerecorded music!\n\n
								It's the latest and greatest\n\n
								show from producer Irving Guice!\n\n"
									#font			bigFont
									#mode			teJustCenter
									#at			-1 30
									#width		234
							)
						)
						(NSpHere
							(Print "Now appearing in the Casino Lounge:\n\n
								Passionate Patti!\n\n
								Keyboard and Sing-A-Long\n
								Star Of Five Continents!\n
								Even Appeared On `Star Search'"
									#font			bigFont
									#mode			teJustCenter
									#at			-1 30
									#width		234
							)
						)
						(else
							(Print "\"Headless Corpse Found in Topless Bar\"")
							(Print "(Don't you just love yellow journalism?!)" LOW)
						)
					)
				)
			)
		)
	)
)

(instance aCredit1 of Prop
	(properties
		view					vMusicBy
;		loop					lMusicBy
;		cel					0
		x						288
		y						131
		cycleSpeed			1
	)

	(method (init)
		(super init)
		(self
			setPri			15,
			ignoreActors,
		)
	)
)
(instance aCredit2 of Prop
	(properties
		view					vMusicBy
		loop					lComposer
;		cel					0
		x						288
		y						154
		cycleSpeed			1
	)

	(method (init)
		(super init)
		(self
			setPri			15,
			ignoreActors,
			setScript		CreditsScript,
		)
	)
)

(instance CreditsScript of Script
	(method (changeState newState)
		(switch (= state newState)

			(doCredits
				(delay 4)
			)

			(doCredits1
				(aCredit1 setCycle EndLoop)
				(= cycles 16)
			)

			(doCredits2
				(aCredit2 setCycle EndLoop)
				(= cycles 22)
			)

			(doCredits3
				(SetFlg sawCredits213)
				(aCredit1 setCycle BegLoop)
				(aCredit2 setCycle BegLoop self)
			)

			(clearCredits
				(aCredit1 dispose)
				(aCredit2 dispose)
			)
		)
	)
)

